# SAGA: Order -> Payment -> Delivery (v1)

## Estados de Order (order-service)
- CREATED
- PAYMENT_PENDING
- PAID
- PAYMENT_FAILED
- DISPATCHED
- DELIVERED
- CANCELLED

## Flujo (happy path)
1. Client crea pedido (REST -> order-service)
2. order-service persiste Order(CREATED) y publica OrderCreated
3. payment-service consume OrderCreated, procesa pago, persiste Payment y publica PaymentSucceeded
4. order-service consume PaymentSucceeded y cambia Order -> PAID
5. delivery-service consume PaymentSucceeded, crea Delivery y publica DeliveryCreated
6. delivery-service actualiza estados y publica DeliveryStatusUpdated (ASSIGNED -> PICKED_UP -> DELIVERED)
7. order-service consume DeliveryStatusUpdated y refleja DISPATCHED/DELIVERED

## Fallo de pago
- payment-service publica PaymentFailed
- order-service cambia Order -> PAYMENT_FAILED (y permite reintentar si se define más adelante)

## Reglas pro (para implementación)
- eventos con eventId único
- consumidores idempotentes (registrar processed events)
- errores permanentes -> DLQ
- key de partición por orderId para orden de eventos