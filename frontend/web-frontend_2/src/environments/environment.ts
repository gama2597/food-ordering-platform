export const environment = {
  production: false,
  keycloak: {
    issuer: 'http://localhost:8080/realms/food-ordering',
    clientId: 'web-frontend',
  },
  apiUrl: 'http://localhost:8081/api/v1', // Ajusta al puerto de tu user-service
};
