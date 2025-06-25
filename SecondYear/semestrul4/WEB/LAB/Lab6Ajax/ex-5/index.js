document.addEventListener('DOMContentLoaded', () => {
    loadDirectory('', document.getElementById('tree-view'));
});

function loadDirectory(path, container) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'server.php', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = () => {
        if (xhr.status === 200) {
            let data;
            try {
                data = JSON.parse(xhr.responseText);
            } catch (e) {
                console.error('Error parsing JSON:', e);
                return;
            }

            const ul = document.createElement('ul');

            if (!Array.isArray(data) || data.length === 0) {
                const emptyMsg = document.createElement('li');
                emptyMsg.textContent = 'No content available.';
                ul.appendChild(emptyMsg);
            } else {
                data.forEach(item => {
                    const li = document.createElement('li');
                    li.textContent = item.name;

                    if (item.type === 'directory') {
                        li.classList.add('folder');
                        li.addEventListener('click', function (e) {
                            e.stopPropagation();
                            if (li.getAttribute('data-loaded')) {
                                const childUl = li.querySelector('ul');
                                if (childUl) {
                                    childUl.style.display = childUl.style.display === 'none' ? 'block' : 'none';
                                }
                            } else {
                                loadDirectory(item.path, li);
                                li.setAttribute('data-loaded', 'true');
                            }
                        });
                    } else if (item.type === 'file') {
                        li.classList.add('file');
                        li.addEventListener('click', function (e) {
                            e.stopPropagation();
                            loadFileContent(item.path);
                        });
                    }

                    ul.appendChild(li);
                });
            }

            container.appendChild(ul);
        } else {
            const ul = document.createElement('ul');
            const emptyMsg = document.createElement('li');
            emptyMsg.textContent = 'No content available.';
            ul.appendChild(emptyMsg);
            container.appendChild(ul);

            console.error('Server error:', xhr.status);
            alert(xhr.response);
        }
    };

    xhr.onerror = () => {
        console.error('Network error');
    };

    xhr.send(JSON.stringify({ path: path }));
}

function loadFileContent(filePath) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'server.php', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = () => {
        if (xhr.status === 200) {
            document.getElementById('file-content').textContent = xhr.responseText;
        } else {
            console.error('Server error while loading file:', xhr.status);
        }
    };

    xhr.onerror = () => {
        console.error('Network error while loading file');
    };

    xhr.send(JSON.stringify({ file: filePath }));
}
