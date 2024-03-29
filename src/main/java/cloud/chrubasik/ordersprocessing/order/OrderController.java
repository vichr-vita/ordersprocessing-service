package cloud.chrubasik.ordersprocessing.order;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cloud.chrubasik.ordersprocessing.order.model.Order;
import cloud.chrubasik.ordersprocessing.order.model.OrderToPost;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("customers/{customerId}/orders")
public class OrderController {


    @Autowired
    OrderService service;

    @Operation(summary = "Gets a list of all orders for customer id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of all orders for customer with {id}", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class))) }), })
    @GetMapping
    public Set<EntityModel<Order>> all(@Parameter(description = "Id of the customer") @PathVariable Long customerId) {
        return service.performListSetForCustomer(customerId);
    }

    @Operation(summary = "Gets detail of one order by id for customer id.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "A detail of an order", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }), })
    @GetMapping("/{orderId}")
    public EntityModel<Order> one(@Parameter(in = ParameterIn.PATH, description = "Id of the customer") @PathVariable Long customerId, @Parameter(in = ParameterIn.PATH, description = "Id of the order") @PathVariable Long orderId) {
        return service.performDetail(orderId, customerId);
    }


    
    @Operation(summary = "Create a new order for customer.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }), })
    @PostMapping
    public EntityModel<Order> postOrder(@PathVariable @Parameter(in = ParameterIn.PATH, description = "customer for whom to create an order") Long customerId,
            @RequestBody final OrderToPost orderFromRequest) {
        return service.performCreate(orderFromRequest, customerId);
    }


    @Operation(summary = "Delete order by id for customer.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }), })
    // @DeleteMapping("/{orderId}")
    @DeleteMapping
    public Order deleteOrder(@Parameter(in = ParameterIn.PATH, description = "customer for whom to delete an order") @PathVariable Long customerId, @RequestParam @Parameter(in = ParameterIn.QUERY, description = "Id of the order to delete") Long orderId) {
        // TODO might return something else (a message with less information)
        return service.performDelete(orderId, customerId);
    }

}
