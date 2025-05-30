/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dspace.app.rest.RestResourceController;

/**
 * Represent a user's request for a copy of an Item.
 * @see org.dspace.app.requestitem
 *
 * @author Mark H. Wood <mwood@iupui.edu>
 */
@LinksRest(links = {
    @LinkRest(name = "bitstream", method = "getUuid"),
    @LinkRest(name = "item", method = "getUuid")
})
public class RequestItemRest extends BaseObjectRest<Integer> {
    public static final String NAME = "itemrequest";
    public static final String PLURAL_NAME = "itemrequests";

    public static final String CATEGORY = RestAddressableModel.TOOLS;

    protected String bitstream_id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Instant decisionDate;

    protected Instant expires;

    protected String item_id;

    protected String reqEmail;

    protected String reqMessage;

    protected String reqName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Instant requestDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String token;

    protected boolean acceptRequest;

    protected boolean allfiles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String accessToken;

    protected Instant accessExpiry;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected boolean accessExpired;

    /**
     * @return the bitstream requested.
     */
    public String getBitstreamId() {
        return bitstream_id;
    }

    /**
     * @param bitstream_id the bitstream requested.
     */
    public void setBitstreamId(String bitstream_id) {
        this.bitstream_id = bitstream_id;
    }

    /**
     * @return the decisionDate
     */
    public Instant getDecisionDate() {
        return decisionDate;
    }

    /**
     * @param decided the decisionDate to set
     */
    public void setDecisionDate(Instant decided) {
        this.decisionDate = decided;
    }

    /**
     * @return the expires
     */
    public Instant getExpires() {
        return expires;
    }

    /**
     * @param expires the expires to set
     */
    public void setExpires(Instant expires) {
        this.expires = expires;
    }

    /**
     * @return the item requested.
     */
    public String getItemId() {
        return item_id;
    }

    /**
     * @param item_id the item requested.
     */
    public void setItemId(String item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the email address of the requester.
     */
    public String getRequestEmail() {
        return reqEmail;
    }

    /**
     * @param email the email address of the requester.
     */
    public void setRequestEmail(String email) {
        this.reqEmail = email;
    }

    /**
     * @return the requester's message.
     */
    public String getRequestMessage() {
        return reqMessage;
    }

    /**
     * @param message the requester's message.
     */
    public void setRequestMessage(String message) {
        this.reqMessage = message;
    }

    /**
     * @return the requester's name.
     */
    public String getRequestName() {
        return reqName;
    }

    /**
     * @param name the requester's name.
     */
    public void setRequestName(String name) {
        this.reqName = name;
    }

    /**
     * @return the requestDate
     */
    public Instant getRequestDate() {
        return requestDate;
    }

    /**
     * @param requested the requestDate to set
     */
    public void setRequestDate(Instant requested) {
        this.requestDate = requested;
    }

    /**
     * @return the token which identifies this request.
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token which identifies this request.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return true if the request has been accepted.
     */
    public boolean isAcceptRequest() {
        return acceptRequest;
    }

    /**
     * @param accepted true if the request has been accepted.
     */
    public void setAcceptRequest(boolean accepted) {
        this.acceptRequest = accepted;
    }

    /**
     * @return true if the request is for all files in the item.
     */
    public boolean isAllfiles() {
        return allfiles;
    }

    /**
     * @param allfiles true requesting all of the item's files.
     */
    public void setAllfiles(boolean allfiles) {
        this.allfiles = allfiles;
    }

    /**
     * @return the unique access token to be used by the requester. This is separate to the approval token ('token')
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the access token to be used by the requester (not settable through JSON update)
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the date the access token expires.
     */
    public Instant getAccessExpiry() {
        return this.accessExpiry;
    }

    /**
     * @param accessExpiry the date the access token expires.
     */
    public void setAccessExpiry(Instant accessExpiry) {
        this.accessExpiry = accessExpiry;
    }

    public boolean isAccessExpired() {
        return accessExpired;
    }

    public void setAccessExpired(boolean accessExpired) {
        this.accessExpired = accessExpired;
    }
    /*
     * Common REST object methods.
     */

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public Class getController() {
        return RestResourceController.class;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getType() {
        return NAME;
    }

    @Override
    public String getTypePlural() {
        return PLURAL_NAME;
    }
}
