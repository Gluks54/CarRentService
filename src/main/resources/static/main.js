const containerEl = document.querySelector("#container");

fetch('/api/allCars')
    .then(response => response.json())
    .then((cars) => {
        const rowsHTML = cars
            .map(car => `
                <tr>
                  <td class="mdl-data-table__cell--non-numeric">${car.model}</td>
                  <td class="mdl-data-table__cell--non-numeric">${car.carBodyType}</td>
                  <td>${car.releaseYear}</td>
                  <td class="mdl-data-table__cell--non-numeric">${car.carStatus}</td>
                  <td>${car.amount} PLN</td>
                </tr>
            `)
            .join('');
        containerEl.innerHTML = `
            <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric">Model</th>
                        <th class="mdl-data-table__cell--non-numeric">Body Type</th>
                        <th>Release Year</th>
                        <th class="mdl-data-table__cell--non-numeric">Car Status</th>
                        <th>Price per day</th>
                    </tr>
                </thead>
                <tbody>${rowsHTML}</tbody>
            </table>
        `;
    });
