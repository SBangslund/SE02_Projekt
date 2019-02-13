window.addEventListener('scroll', () => {
    let parent = document.getElementById('parallax-container');
    let children = parent.getElementsByTagName('div');
    for (let i = 0; i < children.length; i++) {
        if (window.pageYOffset < 1300) {
            children[i].style.transform = 'translateY(-' + (window.pageYOffset * i / children.length) + 'px)';
        } else {
            children[i].style.transform = 'translateY(-1300px)';
        }
    }
}, false);