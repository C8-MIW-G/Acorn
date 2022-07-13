// Enable scrolling in view of profile page elements


function scrollToName() {
    let name = document.getElementById("name change");
    name.scrollIntoView({
        behavior: "smooth",
        block: "center",
        inline: "start"
    });
}

function scrollToPw() {
    let pw = document.getElementById("pw change");
    pw.scrollIntoView({
        behavior: "smooth",
        block: "center",
        inline: "start"
    });
}

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}
mybutton = document.getElementById("myBtn");
function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

