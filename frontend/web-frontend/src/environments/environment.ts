export const environment = {
  production: false,
  keycloak: {
    issuer: 'http://localhost:8080/realms/food-ordering', // Tu realm de Keycloak
    clientId: 'web-frontend' // Tu cliente
  },
  apiUrl: 'http://localhost:8082/api/v1' // Tu backend Spring Boot
};
