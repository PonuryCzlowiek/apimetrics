$(document).ready(function() {
    var ctx = document.getElementById("memory-chart-area").getContext("2d");
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

function getData() {
    $.get("/clients", function (data) {
        resetChart(data);
    })
}

function resetChart(data) {
    var labels = [];
    var values = [];
    for (var value in data) {
        labels.push(value);
        values.push(data[value])
    }
    config.data.datasets[0].data = values;
    config.data.labels = labels;
    window.memoryBarChart.update();
}