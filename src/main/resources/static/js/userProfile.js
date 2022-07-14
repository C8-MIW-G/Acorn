// JQuery doesn't work in this script for whatever reason
function scrollToEmail() {
    document.getElementById("changeEmail").scrollIntoView({
        behavior: "smooth", block: "center", inline: "start" });
}

function scrollToName() {
    document.getElementById("changeName").scrollIntoView({
        behavior: "smooth", block: "center", inline: "start" });
}

function scrollToPw() {
    document.getElementById("changePW").scrollIntoView({
        behavior: "smooth", block: "center", inline: "start" });
}

function scrollToTop() {
    document.getElementById("toTopTarget").scrollIntoView({
        behavior: "smooth", block: "center", inline: "start" });
}

let toTopButton = document.getElementById("toTopButton");

window.onscroll = function() { hideShowToTopButton() };

function hideShowToTopButton() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        toTopButton.style.display = "block";
    } else {
        toTopButton.style.display = "none";
    }
}
