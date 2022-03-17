const previewSection = document.getElementById('preview');
const previewTable = document.getElementById('preview-table');
const previewKey = document.getElementById('preview-key');
const previewEnv = document.getElementById('preview-env');

const progressSection = document.getElementById('progress');
const progressTable = document.getElementById('progress-table');
const downloadBtn = document.getElementById('report');

const hidePreview = () => {
    previewTable.innerHTML = '';
    previewKey.innerText = '';
    previewEnv.innerText = '';
    previewSection.classList.add('d-none');
};

const showPreview = () => {
    previewSection.classList.remove('d-none');
};

const hideProgress = () => {
    progressTable.innerHTML = '';
    progressSection.classList.add('d-none');
};

const showProgress = () => {
    progressSection.classList.remove('d-none');
};

(async () => {
    const openBtn = document.getElementById('open');
    openBtn.addEventListener('click', async () => {
        hidePreview();
        hideProgress();
        const path = document.getElementById('path').value;
        if (!path) {
            alert('Please enter a path');
            return;
        }
        const env = document.forms.item(0).env.value;
        const url = `/preview?env=${env}&path=${path}`;
        const previewRs = await fetch(url);
        if (previewRs.status !== 200) {
            alert(await previewRs.text());
            return;
        }
        const {key, nfts} = await previewRs.json();
        nfts.forEach(nft => {
            const previewHtml = `
                <div class="row">
                    <div class="col">
                        <div class="card mb-3">
                            <div class="card-body">
                                <h5 class="card-title">${nft.name}</h5>
                                <p class="card-text">${nft.description}</p>
                                <p class="card-text"><strong>Supply: </strong>${nft.supply}</p>
                                <p class="card-text"><strong>Asset file: </strong>${nft.asset}</p>
                                <p class="card-text"><strong>Preview file: </strong>${nft.preview}</p>
                                <p class="card-text"><strong>Price: </strong>${nft.price}</p>
                                <p class="card-text"><strong>Joint account: </strong>${nft.ja}</p>
                                <p class="card-text"><strong>Primary cut: </strong>${nft.primaryCut}</p>
                                <p class="card-text"><strong>Secondary cut: </strong>${nft.secondaryCut}</p>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            previewTable.innerHTML += previewHtml;
        });
        previewKey.innerText = key;
        previewEnv.innerText = env;
        showPreview();
    });
    openBtn.disabled = false;

    document.getElementById('cancel').addEventListener('click', () => {
        hidePreview();
    });

    document.getElementById('mint').addEventListener('click', async () => {
        downloadBtn.classList.add('d-none');
        hidePreview();
        showProgress();
        openBtn.disabled = true;
        await fetch('/mint');
        const poll = setInterval(async () => {
            const stateRs = await fetch('/mint/state');
            const {running, progress, error} = await stateRs.json();
            if (!running) {
                if (!error) {
                    alert('Minting complete');
                    downloadBtn.classList.remove('d-none');
                } else {
                    alert(`Minting failed: ${error}`);
                }
                clearInterval(poll)
            }
            progressTable.innerHTML = '';
            progress.forEach(nft => {
                const minted = nft.minted ? '&#9989;' : '...';
                const priceSet = nft.priceSet ? '&#9989;' : '...';
                const royaltySet = nft.royaltySet ? '&#9989;' : '...';
                const assetUploaded = nft.assetUploaded ? '&#9989;' : '...';
                const progressHtml = `
                <tr>
                    <td>${nft.name}</td>
                    <td>${minted}</td>
                    <td>${priceSet}</td>
                    <td>${royaltySet}</td>
                    <td>${assetUploaded}</td>
                </tr>
            `;
                progressTable.innerHTML += progressHtml;
            });
        }, 500);
        openBtn.disabled = false;
    });
})();
