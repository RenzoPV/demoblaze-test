Feature: Flujo de pruebas API PetStore

Background:
    * url baseUrl
 
Scenario: Creación, búsqueda (por id y status) y modificación de mascota
    # Crear mascota
    Given path 'pet'
    And request
    """
    {
        "id": 123456,
        "category": { "id": 1, "name": "Dogs" },
        "name": "Doggie",
        "photoUrls": ["https://example.com/photo1.jpg"],
        "tags": [{ "id": 10, "name": "friendly" }],
        "status": "available"
    }
    """
    When method post
    Then status 200
    * def petId = response.id

    # Buscar mascota por id
    Given path 'pet', petId
    When method get
    Then status 200
    And match response.id == petId

    # Actualizar mascota
    Given path 'pet', petId
    And form field status = 'sold'
    When method post
    Then status 200

    # Buscar por status
    Given path 'pet', 'findByStatus'
    And param status = 'sold'
    When method get
    Then status 200
    And assert response.length > 0
    And match response[*].id contains petId
