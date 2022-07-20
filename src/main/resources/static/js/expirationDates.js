let currentDate;

$(document).ready(function(){
    getCurrentDate();
    let dates = document.querySelectorAll(".expiration-date");
    dates.forEach(dateContainer => compareDates(getProductDate(dateContainer), dateContainer));
});

function getCurrentDate() {
    currentDate = new Date();
    let dd = String(currentDate.getDate()).padStart(2, '0');
    let mm = String(currentDate.getMonth() + 1).padStart(2, '0'); //January is 0!
    let yyyy = currentDate.getFullYear();
    currentDate = yyyy + '/' + mm + '/' + dd;
}

function getProductDate(dateContainer){
    let date = new Date();
    let day = dateContainer.innerHTML.slice(0,2);
    let month = dateContainer.innerHTML.slice(3,5);
    let year = dateContainer.innerHTML.slice(6,10);
    date = year + '/' + month + '/' + day;
    return date;
}

function compareDates(date, dateContainer) {
    if (date < currentDate) {
        dateContainer.classList.add("expired");
    }
}
