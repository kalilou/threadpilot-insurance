package com.threadpilot.vehicle

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return vehicle info for a given person number"
    request {
        method 'GET'
        urlPath('/api/vehicles/owner/199001011235')
    }
    response {
        status 200
        body([
            [
                registrationNumber: "1234567890",
                fuelType: "Petrol",
                model: "Corolla",
                make: "Toyota",
                year: 2020,
                color: "Red",
                ownerPersonalNumber: "199001011235",
                mileage: 1000
            ]
        ])
        headers {
            contentType(applicationJson())
        }
    }
}