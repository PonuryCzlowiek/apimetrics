$(document).ready(function() {
    var ctx = document.getElementById("exceptions-chart-area").getContext("2d");
    window.memoryBarChart = new Chart(ctx, config);
    getData();
});

var config = {
    type: 'line',
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true,
                    stepSize: 1
                }
            }]
        },
        responsive: true
    }
};

function getData() {
    $.get("/exceptionsOccurrences", function (data) {
        resetChart(data);
    })
}

function resetChart(data) {
    var labels = [];
    var values = [];
    var exceptionNames = [];
    for (var value in data) {
        if (data[value] && exceptionNames.length == 0) {
            for (var v in data[value]) {
                exceptionNames.push(v);
            }
        }
        labels.push(value);
        values.push(data[value])
    }

    var newDataSets = [];
    for (var exceptionNumber in exceptionNames) {
        var exceptionOccurrences = values.map(function (v) { return v[exceptionNames[exceptionNumber]] });
        var dataSet = {
            data: exceptionOccurrences,
            label: exceptionNames[exceptionNumber],
            borderColor: utils.color(exceptionNumber)
        };
        newDataSets.push(dataSet);
    }
    config.data.datasets = newDataSets;
    config.data.labels = labels;
    window.memoryBarChart.update();
}