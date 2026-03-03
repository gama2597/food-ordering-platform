# Kafka Topics (v1)

## Topics
- orders.events (key: orderId)
- payments.events (key: orderId)
- deliveries.events (key: orderId)

## Publish / Consume

### order-service
- Publishes: OrderCreated -> orders.events
- Consumes: PaymentSucceeded, PaymentFailed -> payments.events
- Consumes: DeliveryStatusUpdated -> deliveries.events

### payment-service
- Consumes: OrderCreated -> orders.events
- Publishes: PaymentSucceeded, PaymentFailed -> payments.events

### delivery-service
- Consumes: PaymentSucceeded (o OrderPaid) -> payments.events
- Publishes: DeliveryCreated, DeliveryStatusUpdated -> deliveries.events