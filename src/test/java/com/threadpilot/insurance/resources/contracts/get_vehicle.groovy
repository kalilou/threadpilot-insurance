package com.threadpilot.insurance.resources.contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return vehicle by registrationNumber=1234567890"

    request {
        url "/vehicle/1234567890"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (
                registrationNumber: "1234567890",
                model: "Car",
                make: "Toyota",
                year: 2020,
                color: "Red",
                ownerPersonalNumber: "199001011235",
                mileage: 1000
        )
    }
}