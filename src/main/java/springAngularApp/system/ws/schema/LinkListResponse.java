package springAngularApp.system.ws.schema;

import springAngularApp.system.domain.model.IdNameCommand;

import java.util.List;

public class LinkListResponse {

    private List<IdNameCommand> links;

    public LinkListResponse(List<IdNameCommand> links) {
        this.links = links;
    }

    public List<IdNameCommand> getLinks() {
        return links;
    }

    public LinkListResponse() {
    }

}
