export const environment = {
  production: false,
  keycloak: {
    issuer: 'http://localhost:8080/realms/food-ordering', // Tu realm de Keycloak
    clientId: 'web-frontend' // Tu cliente
  },
  apiUrl: 'http://localhost:8081/api/v1' // Tu backend Spring Boot
};
