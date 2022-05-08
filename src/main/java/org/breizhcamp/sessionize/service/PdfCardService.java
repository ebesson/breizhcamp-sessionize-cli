package org.breizhcamp.sessionize.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.breizhcamp.sessionize.model.io.cfp.Format;
import org.breizhcamp.sessionize.model.io.cfp.Proposal;
import org.breizhcamp.sessionize.model.sessionize.session.Sessions;
import org.breizhcamp.sessionize.service.mapper.FormatMapperService;
import org.breizhcamp.sessionize.service.mapper.ProposalMapperService;
import org.breizhcamp.sessionize.service.sessionize.SessionizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.substring;

@Service
public class PdfCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfCardService.class);

    @Autowired
    private SessionizeService sessionService;

    @Autowired
    private ProposalMapperService proposalMapper;

    @Autowired
    private FormatMapperService formatMapper;

    public void export(OutputStream out) throws DocumentException {
            Document document = new Document(PageSize.A4, 10, 10, 10, 10);
            PdfWriter writer = PdfWriter.getInstance(document, out);

            Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);
            Font font6 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.DARK_GRAY);
            Font font10 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontLight = new Font(Font.FontFamily.HELVETICA, 5, Font.NORMAL, BaseColor.DARK_GRAY);

            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);


            Map<Integer, Format> formats = formatMapper.mapFromCategories(sessionService.getAll().getCategories()).stream()
                    .collect(toMap(Format::getId, Function.identity()));

            LOGGER.debug("Nombre de formats", formats.size());

            /*
            List<Proposal> proposals = proposalMapper.findAll(new ProposalQuery().setEventId(eventId).setStates(Arrays.asList(Proposal.State.CONFIRMED)));


            List<Rate> rates = this.rateMapper.findAllWithTalk(eventId);
            Map<Integer, List<Rate>> ratesByTalk = rates.stream().collect(Collectors.groupingBy(rate -> rate.getTalk().getId()));

            for (Proposal proposal : proposals) {
                List<String> emails = new ArrayList<>();
                float total = 0;
                int votes = 0;
                for (Rate rate : ratesByTalk.get(proposal.getId())) {
                    emails.add(rate.getUser().getEmail());
                    if (rate.getRate() > 0) {
                        total += rate.getRate();
                        votes++;
                    }
                }
                proposal.setVoteUsersEmail(emails);
                if (votes > 0) {
                    proposal.setMean(String.valueOf(total / votes));
                }
            }
            */
            List<Proposal> sortedProposals = new ArrayList<>();
            List<Sessions> sessions = sessionService.getSessions();
            for (Sessions session : sessions){
                sortedProposals.addAll(proposalMapper.mapFromSessionizeTrack(session));
            }
            /*List<Proposal> sortedProposals = proposals.stream()
                    .sorted(comparing(Proposal::getFormat).thenComparing(Proposal::getMean, nullsLast(reverseOrder())))
                    .collect(toList());
            */
            LOGGER.info("Export PDF de {} Proposals", sortedProposals.size());

            Map<Integer, BaseColor> bgTracksColor = new HashMap<>();

            for (Proposal proposal : sortedProposals) {

                // Tableau pour chaque talk
                PdfPTable innerTable = new PdfPTable(2);
                innerTable.setWidthPercentage(100);
                //innerTable.widths = [1f, 1f]

                // En-tête (format + track)
                Phrase formatPh = new Phrase(new Chunk(formats.get(proposal.getFormat()).getName(), font));
                PdfPCell format = new PdfPCell(formatPh);

                innerTable.addCell(format);
                PdfPCell track = new PdfPCell(new Phrase(substring(proposal.getTrackLabel(), 0, 20), font));

                track.setHorizontalAlignment(Element.ALIGN_RIGHT);
                track.setBackgroundColor(bgTracksColor.computeIfAbsent(proposal.getTrackId(), id -> getColor(bgTracksColor.size() + 1)));
                innerTable.addCell(track);

                // Contenu central
                PdfPCell cellCentrale = new PdfPCell();
                cellCentrale.setColspan(2);
                cellCentrale.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cellCentrale.setFixedHeight(100.0f);

                String speaker = proposal.getSpeaker().getShortName();
                if (isNotEmpty(proposal.getCospeakers())) {
                    speaker += " (+" + proposal.getCospeakers().size() + ")";
                }
                Paragraph spk = new Paragraph(speaker, font);
                spk.setAlignment(Paragraph.ALIGN_CENTER);
                cellCentrale.addElement(spk);

                Paragraph ttl = new Paragraph(10, proposal.getName(), font10);
                ttl.setAlignment(Paragraph.ALIGN_CENTER);
                ttl.setSpacingBefore(3);
                ttl.setKeepTogether(true);
                cellCentrale.addElement(ttl);

                Paragraph description;
                int sizeMaxDesc = 400;
                String desc = proposal.getDescription();
                if (desc.length() > sizeMaxDesc) {
                    description = new Paragraph(new Paragraph(desc.substring(0, sizeMaxDesc).replaceAll("\n\n", "\n"), font6));
                } else {
                    description = new Paragraph(new Paragraph(desc.replaceAll("\n\n", "\n"), font6));
                }

                description.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                description.setSpacingBefore(3);
                cellCentrale.addElement(description);

                innerTable.addCell(cellCentrale);

                // Note sur ligne du bas
                BarcodeEAN barcode = new BarcodeEAN();
                barcode.setCodeType(Barcode.EAN8);
                String code = "9" + StringUtils.leftPad(Integer.toString(proposal.getId()), 6, "0");
                barcode.setCode(code + BarcodeEAN.calculateEANParity(code));
                barcode.setBarHeight(15);
                barcode.setFont(null);

                Phrase barcodePh = new Phrase();
                PdfPCell barCell = new PdfPCell(barcodePh);
                barcodePh.add(new Chunk(barcode.createImageWithBarcode(writer.getDirectContent(), null, null), 0, 0));
                barcodePh.add(new Chunk("     " + proposal.getId(), fontLight));

                barCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                barCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                barCell.setPadding(1f);
                barCell.setPaddingLeft(8f);
                barCell.setFixedHeight(15);
                innerTable.addCell(barCell);


                PdfPCell note = new PdfPCell(new Phrase("note : " + defaultIfNull(proposal.getMean(), "N/A"), font));
                note.setHorizontalAlignment(Element.ALIGN_RIGHT);
                note.setVerticalAlignment(Element.ALIGN_BOTTOM);
                innerTable.addCell(note);

                table.addCell(innerTable);

            }

            for (int i = 0; i < sortedProposals.size() % 3; i++) {
                table.addCell("");
            }

            document.add(table);

            document.close();

        }


        private BaseColor getColor ( int idx){
            switch (idx) {
                case 1:
                    return BaseColor.MAGENTA;
                case 2:
                    return BaseColor.PINK;
                case 3:
                    return BaseColor.YELLOW;
                case 4:
                    return BaseColor.GREEN;
                case 5:
                    return BaseColor.ORANGE;
                case 6:
                    return BaseColor.GRAY;
                case 7:
                    return BaseColor.RED;
                case 8:
                    return BaseColor.CYAN;
                case 9:
                    return BaseColor.LIGHT_GRAY;
                default:
                    return BaseColor.WHITE;
            }
        }
    }