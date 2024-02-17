var BACKEND_BASE_URL = "http://localhost:8080/";

var supportedCurrencies = [
    {
        "code": "EUR",
        "name": "Euro"
    }, {
        "code": "INR",
        "name": "Indian Rupee"
    }, {
        "code": "JPY",
        "name": "Japanese Yen"
    }, {
        "code": "USD",
        "name": "US Dollar"
    }
];

function getFullPath(from, to, quantity, date) {
    var url = BACKEND_BASE_URL + "currency-conversion/convert/" + from + "/" + to + "/" + quantity;
    if (date != null && date != '') {
        url = url + "?date=" + date;
    }
    return url;
}

function populateCurrenciesDropdowns(elementId) {
    var dropdown = document.getElementById(elementId);

    supportedCurrencies.forEach(function(currency) {
        var optionElement = document.createElement("option");
        optionElement.text = currency.name;
        optionElement.value = currency.code;
        dropdown.appendChild(optionElement);
    });
}

function toggleDateSelection() {
    var dateInputContainer = document.getElementById("dateInputContainer");
    var customButton = document.getElementById("customDate");

    if (customButton.checked) {
        dateInputContainer.style.display = "block";
    } else {
        dateInputContainer.style.display = "none";
    }
}

document.addEventListener("DOMContentLoaded", function() {
    populateCurrenciesDropdowns("fromCurrency");
    populateCurrenciesDropdowns("toCurrency");

    document.getElementById("latestDate").checked = true;
    document.getElementById("quantity").value = 0;

    document.querySelectorAll('input[name="dateSelection"]').forEach(function(radioButton) {
        radioButton.addEventListener("change", toggleDateSelection);
    });

    var today = new Date();
    var threeMonthsAgo = new Date(today.getFullYear(), today.getMonth() - 2, today.getDate());
    var formattedDate = threeMonthsAgo.toISOString().slice(0, 10);
    document.getElementById("date").setAttribute("max", today.toISOString().slice(0, 10));
    document.getElementById("date").setAttribute("min", formattedDate);

    document.getElementById("currenciesConversionForm").addEventListener("submit", function(event) {
        event.preventDefault();
    
        var fromCurrency = document.getElementById("fromCurrency").value;
        var toCurrency = document.getElementById("toCurrency").value;
        var quantity = document.getElementById("quantity").value;
        var dateSelection = document.getElementById("customDate");
        var date = dateSelection.checked ? document.getElementById("date").value : null;
    
        var url = getFullPath(fromCurrency, toCurrency, quantity, date);
    
        fetch(url, {
            method: "POST"
        }).then(response => {
            if (!response.ok) {
                throw new Error("Failed to convert currency");
            }
            return response.json();
        }).then(data => {
            document.getElementById("output").textContent = data.outputQuantity;
        }).catch(error => {
            alert("Error from API" + error);
        });
    });
});