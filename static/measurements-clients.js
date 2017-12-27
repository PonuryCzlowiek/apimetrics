$(document).ready(function() {
    var ctx = document.getElementById("clients-chart-area").getContext("2d");
    window.memoryBarChart = new Chart(ctx, config);
    getData();
});

var config = {
    type: 'pie',
    data: {
        datasets: [{
            data: [
            ],
            backgroundColor: ["#3e95cd", "#cd494c", "#40cd3a", "#35C5CD", "#A568CD", "#C6CD50" ,"#0000FF", "#00FF00", "#FF0000"]
        }]
    },
    options: {
        responsive: true
    }
};

function getData(field) {
    $.get("/clients", function (data) {
        resetChart(data, field);
    })
}

function resetChart(data, field) {
    var labels = [];
    var values = [];
    for (var value in data[field]) {
        labels.push(value);
        values.push(data[field][value])
    }
    config.data.datasets[0].data = values;
    config.data.labels = labels;
    window.memoryBarChart.update();
}