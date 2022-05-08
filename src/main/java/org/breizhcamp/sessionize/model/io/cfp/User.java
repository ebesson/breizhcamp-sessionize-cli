/*
 * Copyright (c) 2016 BreizhCamp
 * [http://breizhcamp.org]
 *
 * This file is part of CFP.io.
 *
 * CFP.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.breizhcamp.sessionize.model.io.cfp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    public static final List<String> AUTHORIZED_SORTS = Arrays.asList("lastname", "firstname", "gender", "language", "company");

    public enum Gender {
        MALE, FEMALE
    }

    public enum TshirtSize {
        XS, S, M, L, XL, XXL
    }

    private String id;
    private String email;
    private String lastname;
    private String firstname;
    private String company;
    private String phone;
    private String bio;
    private String twitter;
    private String googleplus;
    private String github;
    private String language;
    private Gender gender = Gender.MALE;
    private TshirtSize tshirtSize = TshirtSize.L;
    private String social;
    private String imageProfilURL;
    private Set<String> roles;

    public User addRole(String... roles) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.addAll(Arrays.asList(roles));
        return this;
    }

    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    public String getShortName() {
        String res = "";
        if (firstname != null && firstname.length() > 0) {
            res += firstname.charAt(0) + ". ";
        }

        if (lastname != null) {
            res += lastname;
        }

        return res;
    }

    public String getFullName() {
        return this.firstname + " " + this.lastname;
    }

    public boolean isReviewer() {
        return hasRole(Role.REVIEWER);
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }

    public boolean isOwner() {
        return hasRole(Role.OWNER);
    }

    public Set<String> getRoles() {
        return roles != null ? roles : Collections.emptySet();
    }


    /**
     * Used to trace current user in application logs
     */
    public String toLog() {
        return email + '(' + StringUtils.join(roles) + ')';
    }

    public Locale getLocale() {
        if (language != null && (language.equalsIgnoreCase("français") || language.equalsIgnoreCase("fr"))) {
            return Locale.FRENCH;
        }
        return Locale.ENGLISH;
    }

    public User cleanPrivatesInformations() {
        this.setEmail(null);
        this.setTshirtSize(null);
        this.setPhone(null);
        this.setRoles(null);
        return this;
    }

    public boolean isOneOfTheSpeakersOf(Proposal proposal) {
        return proposal.getSpeakersIds().contains(id);
    }
}

