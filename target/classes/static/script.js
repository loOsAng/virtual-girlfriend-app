document.addEventListener('DOMContentLoaded', () => {
    // --- DOM Elements ---
    const toggleLeftSidebarBtn = document.getElementById('toggle-left-sidebar');
    const leftSidebar = document.getElementById('left-sidebar');
    const toggleCreationPanelBtn = document.getElementById('toggle-creation-panel');
    const creationPanelContent = document.getElementById('creation-panel-content');

    const girlfriendListContainer = document.getElementById('girlfriend-list-container');
    const gfNameTitleSidebar = document.getElementById('gf-name-title-sidebar');
    // Detail spans in sidebar
    const gfNameSidebar = document.getElementById('gf-name-sidebar');
    const gfAgeSidebar = document.getElementById('gf-age-sidebar');
    const gfHomeSidebar = document.getElementById('gf-home-sidebar');
    const gfWorkSidebar = document.getElementById('gf-work-sidebar');
    const gfHobbySidebar = document.getElementById('gf-hobby-sidebar');
    const gfCharacterSidebar = document.getElementById('gf-character-sidebar'); // Will display gfData.characterAttr
    const gfBirthdaySidebar = document.getElementById('gf-birthday-sidebar');
    const gfFromSidebar = document.getElementById('gf-from-sidebar');       // Will display gfData.fromWorld

    const girlfriendForm = document.getElementById('girlfriend-form');
    const gfIdFormInput = document.getElementById('gf-id-form');
    const nameInput = document.getElementById('name');
    const ageInput = document.getElementById('age');
    const homeInput = document.getElementById('home');
    const workInput = document.getElementById('work');
    const hobbyInput = document.getElementById('hobby');
    const characterInput = document.getElementById('character'); // Input field for character
    const birthdayInput = document.getElementById('birthday');
    const fromInput = document.getElementById('from');           // Input field for from
    const submitGfFormBtn = document.getElementById('submit-gf-form');
    const newGfBtn = document.getElementById('new-gf-btn');

    const actionButtonsContainer = document.querySelector('.action-buttons');
    const actionResultDiv = document.getElementById('action-result');

    const chatLog = document.getElementById('chat-log');
    const chatInput = document.getElementById('chat-input');
    const sendChatBtn = document.getElementById('send-chat-btn');
    const gfChatNameSpan = document.getElementById('gf-chat-name');

    const API_BASE_URL = 'http://localhost:8080/api/girlfriends';

    // --- State ---
    let allGirlfriendsData = [];
    let currentSelectedGfId = null;

    // --- Sidebar & Panel Toggle Logic ---
    if (toggleLeftSidebarBtn && leftSidebar) {
        toggleLeftSidebarBtn.addEventListener('click', () => {
            leftSidebar.classList.toggle(window.innerWidth <= 768 ? 'open' : 'hidden');
        });
    }
    if (toggleCreationPanelBtn && creationPanelContent) {
        toggleCreationPanelBtn.addEventListener('click', () => {
            const বর্তমানেদৃশ্যমান = creationPanelContent.classList.contains('open') || !creationPanelContent.classList.contains('hidden');
            creationPanelContent.classList.toggle(window.innerWidth <= 768 ? 'open' : 'hidden');
            
            // Open panel specific logic
            if (creationPanelContent.classList.contains('open') || 
               (window.innerWidth > 768 && !creationPanelContent.classList.contains('hidden'))) {
                if (currentSelectedGfId && submitGfFormBtn.textContent.includes("创建")) { // If new button was clicked then form was reset
                     // If a GF is selected, and we are not in "new" mode, populate for edit
                    const selectedGf = allGirlfriendsData.find(gf => gf.id === currentSelectedGfId);
                    if (selectedGf) populateFormForEdit(selectedGf);
                } else if (!currentSelectedGfId) { // No GF selected, or new was clicked
                    prepareFormForNew();
                }
            }
        });
    }

    // --- Girlfriend List Rendering & Selection ---
    function renderGirlfriendList() {
        console.log("renderGirlfriendList called. All GFs:", allGirlfriendsData, "Selected ID:", currentSelectedGfId);
        girlfriendListContainer.innerHTML = '';
        if (!allGirlfriendsData || allGirlfriendsData.length === 0) {
            const li = document.createElement('li');
            li.textContent = '暂无伴侣数据';
            li.style.padding = '10px 15px';
            li.style.color = '#aaa';
            girlfriendListContainer.appendChild(li);
            return;
        }

        allGirlfriendsData.forEach(gf => {
            const li = document.createElement('li');
            li.classList.add('list-item');
            li.textContent = gf.name;
            li.dataset.gfId = gf.id;
            if (gf.id === currentSelectedGfId) {
                li.classList.add('active');
            }
            li.addEventListener('click', () => selectGirlfriend(gf.id));
            girlfriendListContainer.appendChild(li);
        });
    }

    async function selectGirlfriend(gfId) {
        console.log("Attempting to select girlfriend with ID:", gfId);
        const selectedGf = allGirlfriendsData.find(gf => gf.id === gfId);
        if (selectedGf) {
            currentSelectedGfId = gfId;
            updateGirlfriendDetailsUI(selectedGf); // Updates sidebar details and chat name
            renderGirlfriendList(); // Updates active class in the list

            console.log("Calling loadAndDisplayChatHistory for ID:", gfId);
            await loadAndDisplayChatHistory(gfId);

            // If creation panel is visible, populate form for edit
            const isCreationPanelVisible = (window.innerWidth > 768 && !creationPanelContent.classList.contains('hidden')) ||
                                       (window.innerWidth <= 768 && creationPanelContent.classList.contains('open'));
            if (isCreationPanelVisible) {
                 populateFormForEdit(selectedGf);
            }

        } else {
            console.error("Could not find girlfriend with ID:", gfId, "in allGirlfriendsData:", allGirlfriendsData);
            updateGirlfriendDetailsUI(null); // Clear details if GF not found
        }
    }

    // --- Load and Display Chat History ---
    async function loadAndDisplayChatHistory(gfId) {
        chatLog.innerHTML = '<p style="text-align:center; color:#aaa;">正在加载聊天记录...</p>';
        if (!gfId) {
            chatLog.innerHTML = '<p style="text-align:center; color:#aaa;">请先选择一位伴侣。</p>';
            console.warn("loadAndDisplayChatHistory: gfId is null or undefined.");
            return;
        }

        console.log("Fetching chat history for gfId:", gfId, "from URL:", `${API_BASE_URL}/${gfId}/chat-history`);
        try {
            const response = await fetch(`${API_BASE_URL}/${gfId}/chat-history`);
            console.log("Chat history response status:", response.status);

            if (response.ok) {
                const history = await response.json();
                console.log("Received chat history:", history);

                chatLog.innerHTML = '';
                if (history.length === 0) {
                    chatLog.innerHTML = '<p style="text-align:center; color:#aaa;">还没有聊天记录哦，快开始对话吧！</p>';
                } else {
                    history.forEach(chatMsg => {
                        // console.log("Appending message to chat log:", chatMsg);
                        appendToChatLog(chatMsg.message, chatMsg.sender);
                    });
                }
            } else {
                const errorText = await response.text();
                chatLog.innerHTML = `<p style="text-align:center; color:red;">加载聊天记录失败 (状态: ${response.status})。</p>`;
                console.error("Failed to load chat history, status:", response.status, "Response text:", errorText);
            }
        } catch (error) {
            chatLog.innerHTML = '<p style="text-align:center; color:red;">加载聊天记录时发生网络错误。</p>';
            console.error("Error loading chat history:", error);
        }
    }

    // --- Update UI with GF Details ---
    function updateGirlfriendDetailsUI(gfData) {
        console.log("updateGirlfriendDetailsUI called with data:", gfData);
        if (!gfData) {
            gfNameTitleSidebar.textContent = '未选择';
            gfNameSidebar.textContent = '-';
            gfAgeSidebar.textContent = '-';
            gfHomeSidebar.textContent = '-';
            gfWorkSidebar.textContent = '-';
            gfHobbySidebar.textContent = '-';
            gfCharacterSidebar.textContent = '-';
            gfBirthdaySidebar.textContent = '-';
            gfFromSidebar.textContent = '-';
            gfChatNameSpan.textContent = '未选择';
            currentSelectedGfId = null; // Ensure currentSelectedGfId is also cleared
            chatLog.innerHTML = '<p style="text-align:center; color:#aaa;">请先选择一位伴侣。</p>';
            disableInteractionAndChat();
            return;
        }
        gfNameTitleSidebar.textContent = gfData.name;
        gfNameSidebar.textContent = gfData.name || '-';
        gfAgeSidebar.textContent = gfData.age || '-';
        gfHomeSidebar.textContent = gfData.home || '-';
        gfWorkSidebar.textContent = gfData.work || '-';
        gfHobbySidebar.textContent = gfData.hobby || '-';
        gfCharacterSidebar.textContent = gfData.characterAttr || '-'; // Use characterAttr
        gfBirthdaySidebar.textContent = gfData.birthday || '-';
        gfFromSidebar.textContent = gfData.fromWorld || '-';       // Use fromWorld
        gfChatNameSpan.textContent = gfData.name;
        enableInteractionAndChat();
    }

    function disableInteractionAndChat() {
        actionButtonsContainer.style.opacity = '0.5';
        actionButtonsContainer.style.pointerEvents = 'none';
        chatInput.disabled = true;
        sendChatBtn.disabled = true;
    }

    function enableInteractionAndChat() {
        actionButtonsContainer.style.opacity = '1';
        actionButtonsContainer.style.pointerEvents = 'auto';
        chatInput.disabled = false;
        sendChatBtn.disabled = false;
    }

    // --- Form Handling ---
    function populateFormForEdit(gfData) {
        console.log("Populating form for edit:", gfData);
        gfIdFormInput.value = gfData.id;
        nameInput.value = gfData.name || '';
        ageInput.value = gfData.age || '';
        homeInput.value = gfData.home || '';
        workInput.value = gfData.work || '';
        hobbyInput.value = gfData.hobby || '';
        characterInput.value = gfData.characterAttr || ''; // Populate from characterAttr
        birthdayInput.value = gfData.birthday || '';
        fromInput.value = gfData.fromWorld || '';         // Populate from fromWorld
        submitGfFormBtn.innerHTML = '<i class="fas fa-save"></i> 更新设定';
    }

    function prepareFormForNew() {
        console.log("Preparing form for new GF.");
        girlfriendForm.reset();
        gfIdFormInput.value = '';
        submitGfFormBtn.innerHTML = '<i class="fas fa-plus-circle"></i> 创建角色';
        nameInput.focus();
    }

    newGfBtn.addEventListener('click', () => {
        prepareFormForNew();
        currentSelectedGfId = null; 
        // Ensure creation panel is visible
        const isCreationPanelHiddenOnDesktop = window.innerWidth > 768 && creationPanelContent.classList.contains('hidden');
        const isCreationPanelNotOpenOnMobile = window.innerWidth <= 768 && !creationPanelContent.classList.contains('open');

        if (isCreationPanelHiddenOnDesktop || isCreationPanelNotOpenOnMobile) {
            creationPanelContent.classList.toggle(window.innerWidth <= 768 ? 'open' : 'hidden');
        }
    });

    girlfriendForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const gfDataPayload = {
            name: nameInput.value,
            age: parseInt(ageInput.value) || 0,
            home: homeInput.value,
            work: workInput.value,
            hobby: hobbyInput.value,
            characterAttr: characterInput.value, // Send as characterAttr
            birthday: birthdayInput.value,
            fromWorld: fromInput.value,         // Send as fromWorld
        };
        console.log("Submitting GF data:", gfDataPayload);

        const currentId = gfIdFormInput.value;
        const method = currentId ? 'PUT' : 'POST';
        const url = currentId ? `${API_BASE_URL}/${currentId}` : API_BASE_URL;

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(gfDataPayload)
            });

            if (response.ok) {
                const savedGf = await response.json();
                alert(currentId ? '伴侣信息已更新!' : '新伴侣已创建!');
                await loadAllGirlfriends();
                await selectGirlfriend(savedGf.id);
                // Optionally hide creation panel on small screens after save
                if (window.innerWidth <= 768 && creationPanelContent.classList.contains('open')) {
                    creationPanelContent.classList.remove('open');
                }
                // Don't call prepareFormForNew() here, as user might want to see/edit the saved data
            } else {
                const errorData = await response.json().catch(() => ({ message: "未知错误，无法解析响应体" }));
                alert('操作失败: ' + (errorData.message || response.statusText || '未知错误'));
                console.error("GF form submission error:", errorData);
            }
        } catch (error) {
            alert('操作时发生网络错误: ' + error.message);
            console.error("GF form submission network error:", error);
        }
    });

    // --- Load All Girlfriends on Init ---
    async function loadAllGirlfriends() {
        console.log("loadAllGirlfriends called");
        try {
            const response = await fetch(API_BASE_URL);
            if (response.ok) {
                allGirlfriendsData = await response.json();
                console.log("Fetched all GFs:", allGirlfriendsData);
                renderGirlfriendList();
                if (allGirlfriendsData.length > 0 && !currentSelectedGfId) {
                    console.log("Initial load: Selecting first girlfriend.");
                    await selectGirlfriend(allGirlfriendsData[0].id);
                } else if (allGirlfriendsData.length === 0) {
                    updateGirlfriendDetailsUI(null);
                    prepareFormForNew();
                } else if (currentSelectedGfId) {
                    const stillExists = allGirlfriendsData.find(gf => gf.id === currentSelectedGfId);
                    if (stillExists) {
                        console.log("Refresh: Reselecting current girlfriend ID:", currentSelectedGfId);
                        await selectGirlfriend(currentSelectedGfId);
                    } else {
                        console.log("Previously selected GF (ID:", currentSelectedGfId, ") no longer exists. Clearing selection.");
                        updateGirlfriendDetailsUI(null);
                        currentSelectedGfId = null;
                        if(allGirlfriendsData.length > 0) {
                            console.log("Selecting first available GF after previous was not found.");
                            await selectGirlfriend(allGirlfriendsData[0].id);
                        }
                    }
                }
            } else {
                alert('加载伴侣列表失败!');
                console.error("Failed to load all GFs, status:", response.status);
                allGirlfriendsData = [];
                renderGirlfriendList();
                updateGirlfriendDetailsUI(null);
            }
        } catch (error) {
            alert('加载伴侣列表时出错: ' + error.message);
            console.error("Error in loadAllGirlfriends:", error);
            allGirlfriendsData = [];
            renderGirlfriendList();
            updateGirlfriendDetailsUI(null);
        }
    }

    // --- Actions & Chat ---
    actionButtonsContainer.addEventListener('click', async (event) => {
        const button = event.target.closest('button');
        if (!button || !button.dataset.action) return;
        if (!currentSelectedGfId) {
            alert('请先选择一个伴侣进行互动!');
            return;
        }
        const action = button.dataset.action;
        actionResultDiv.textContent = '处理中...';
        actionResultDiv.classList.remove('error');
        actionResultDiv.classList.add('show');

        try {
            const response = await fetch(`${API_BASE_URL}/${currentSelectedGfId}/action/${action}`, { method: 'POST' });
            if (response.ok) {
                const result = await response.text();
                actionResultDiv.textContent = result;
            } else {
                const errorText = await response.text();
                actionResultDiv.textContent = `操作 ${action} 失败! ${errorText || ''}`;
                actionResultDiv.classList.add('error');
            }
        } catch (error) {
            actionResultDiv.textContent = `操作 ${action} 时出错: ${error.message}`;
            actionResultDiv.classList.add('error');
        }
    });

    function appendToChatLog(message, sender) {
        // console.log(`Appending to log: Sender=${sender}, Message=${message.substring(0,30)}...`);
        const messageDiv = document.createElement('div');
        messageDiv.innerHTML = message.replace(/\n/g, '<br>');
        messageDiv.classList.add(sender === 'user' ? 'chat-log-user' : 'chat-log-gf');
        if (sender === 'system') { // For system messages like errors from AI parsing
            messageDiv.classList.add('chat-log-system');
             messageDiv.style.textAlign = 'center';
             messageDiv.style.color = 'gray';
             messageDiv.style.fontStyle = 'italic';
             messageDiv.style.fontSize = '0.9em';
        }
        chatLog.appendChild(messageDiv);
        chatLog.scrollTop = chatLog.scrollHeight;
    }

    async function sendChatMessage() {
        if (!currentSelectedGfId) {
            alert('请先选择一个伴侣进行聊天!');
            return;
        }
        const userInput = chatInput.value.trim();
        if (!userInput) return;

        appendToChatLog(userInput, 'user');
        // Optimistically save user message to local chat log, backend will save it too.

        chatInput.value = ''; // Clear input after appending

        try {
            const response = await fetch(`${API_BASE_URL}/${currentSelectedGfId}/chat`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: userInput })
            });

            if (response.ok) {
                const gfResponse = await response.text();
                appendToChatLog(gfResponse, 'girlfriend');
            } else {
                const errorText = await response.text();
                appendToChatLog(`聊天出错 (对方): ${errorText || response.statusText}`, 'system');
            }
        } catch (error) {
            appendToChatLog(`聊天时发生网络错误: ${error.message}`, 'system');
        }
    }

    sendChatBtn.addEventListener('click', sendChatMessage);
    chatInput.addEventListener('keypress', (event) => { if (event.key === 'Enter') sendChatMessage(); });

    // --- Initial Load ---
    updateGirlfriendDetailsUI(null);
    loadAllGirlfriends();
});