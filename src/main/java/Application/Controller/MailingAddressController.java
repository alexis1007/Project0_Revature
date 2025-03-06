package Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.MailingAddress;
import Application.Service.MailingAddressService;
import io.javalin.http.Context;

public class MailingAddressController {
    private static final Logger logger = LoggerFactory.getLogger(MailingAddressController.class);
    private final MailingAddressService mailingAddressService;

    public MailingAddressController() {
        this.mailingAddressService = new MailingAddressService();
    }

    public void getAllAddressesHandler(Context ctx) {
        ctx.json(mailingAddressService.getAllMailingAddresses());
    }

    public void getAddressByIdHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        MailingAddress address = mailingAddressService.getMailingAddressById(id);
        if (address != null) {
            ctx.json(address);
        } else {
            ctx.status(404).json("Address not found");
        }
    }

    public void createAddressHandler(Context ctx) {
        try {
            MailingAddress address = ctx.bodyAsClass(MailingAddress.class);
            MailingAddress created = mailingAddressService.createMailingAddress(address);
            
            if (created != null) {
                ctx.status(201).json(created);
            } else {
                ctx.status(400).json("Invalid address data");
            }
        } catch (Exception e) {
            logger.error("Error creating address", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void updateAddressHandler(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MailingAddress address = ctx.bodyAsClass(MailingAddress.class);
            mailingAddressService.updateMailingAddress(id, address);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error updating address", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void deleteAddressHandler(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            mailingAddressService.deleteMailingAddress(id);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error deleting address", e);
            ctx.status(500).json("Internal server error");
        }
    }
}
