import {createStore} from "vuex";
import axios from "axios";

export default createStore({
    state: {
        // 전역으로 관리할 상태(데이터) 목록
        multiTenantEnable: false,
        menus: [],
        selectedTopMenu: {},
        defaultHomeUrl: "",
        selectedSubMenu: [],
        currentUser: null,
        frontRouters: [],
        loggedIn: false,
        warningAlert: {
            title: "",
            message: "",
            visible: false,
            action: null,
        },
        saveAlert: {
            title: "",
            message: "",
            visible: false,
            action: null,
        },
        deleteAlert: {
            title: "",
            message: "",
            visible: false,
            action: null,
        },
        popupAlert: {
            title: "",
            message: "",
            visible: false,
        },
        popupDismissedToday: false,
    },
    getters: {
        getMenus: (state) => state.menus,
        getDefaultHomeUrl: (state) => state.defaultHomeUrl,
        getSelectedTopMenu: (state) => state.selectedTopMenu,
        getSelectedSubMenu: (state) => state.selectedSubMenu,
        getCurrentUser: (state) => state.currentUser,
        getFrontRouters: (state) => state.frontRouters,
        isLoggedIn: (state) => state.loggedIn,
        isExist: (state) => state.selectedSubMenu.pageUrl,
        warningAlert: (state) => state.warningAlert,
        saveAlert: (state) => state.saveAlert,
        deleteAlert: (state) => state.deleteAlert,
        popupAlert: (state) => state.popupAlert,
        popupDismissedToday: (state) => state.popupDismissedToday,
    },
    mutations: {
        setMenus: (state, menus) => {
            state.menus = menus;
        },
        setDefaultHomeUrl: (state, defaultHomeUrl) => {
            state.defaultHomeUrl = defaultHomeUrl;
        },
        setSelectedTopMenu: (state, selectedTopMenu) => {
            state.selectedTopMenu = selectedTopMenu;
        },
        setSelectedSubMenu: (state, selectedSubMenu) => {
            state.selectedSubMenu = selectedSubMenu;
        },
        setCurrentUser: (state, currentUser) => {
            state.currentUser = currentUser;
        },
        setMultiTenantEnable: (state, multiTenantEnable) => {
            state.multiTenantEnable = multiTenantEnable;
        },
        login: (state) => {
            state.loggedIn = true;
        },
        logout: (state) => {
            state.loggedIn = false;
        },
        setFrontRouters: (state, frontRouters) => {
            state.frontRouters = frontRouters;
        },
        showWarningAlert: (state, { title, message, action }) => {
            state.warningAlert.title = title;
            state.warningAlert.message = message;
            state.warningAlert.action = action;
            state.warningAlert.visible = true;
        },
        hideWarningAlert: (state) => {
            state.warningAlert.message = "";
            state.warningAlert.visible = false;
        },
        showSaveAlert: (state, { title, message, action }) => {
            state.saveAlert.title = title;
            state.saveAlert.message = message;
            state.saveAlert.action = action;
            state.saveAlert.visible = true;
        },
        hideSaveAlert: (state) => {
            state.saveAlert.message = "";
            state.saveAlert.visible = false;
        },
        showDeleteAlert: (state, { title, message, action }) => {
            state.deleteAlert.title = title;
            state.deleteAlert.message = message;
            state.deleteAlert.action = action;
            state.deleteAlert.visible = true;
        },
        hideDeleteAlert: (state) => {
            state.deleteAlert.message = "";
            state.deleteAlert.visible = false;
        },
        showPopupAlert: (state, { title, message }) => {
            state.popupAlert.title = title;
            state.popupAlert.message = message;
            state.popupAlert.visible = true;
        },
        hidePopupAlert: (state) => {
            state.popupAlert.message = "";
            state.popupAlert.visible = false;
        },
        setPopupDismissedToday: (state, dismissed) => {
            state.popupDismissedToday = dismissed;
        },
    },
    actions: {
        fetchMenus: async ({ commit }) => {
            const response = await axios.get("/api/menu/list");
            commit("setMenus", response.data);
        },
        fetchDefaultHomeUrl: async ({ commit }) => {
            const response = await axios.get("/api/menu/default/home");
            commit("setDefaultHomeUrl", response.data);
        },
        fetchCurrentUser: async ({ commit }) => {
            const response = await axios.get("/api/auth/current");
            commit("setCurrentUser", response.data);
        },
        fetchFrontRouter: async ({ commit }) => {
            const response = await axios.get("/api/front/routers");
            commit("setFrontRouters", response.data);
        },
        fetchMultiTenantEnable: async ({ commit }) => {
            const response = await axios.get("/api/tenant/enable");
            commit("setMultiTenantEnable", response.data);
        },
        fetchBoardData: async ({ commit, state }) => {
            if (state.popupDismissedToday) return;

            const response = await axios.get("/api/board/list");
            response.data.forEach((board) => {
                const additionalData = board.boardAdditionalData
                    ? JSON.parse(board.boardAdditionalData)
                    : {};
                if (additionalData.showPopup === true) {
                    commit("showPopupAlert", {
                        title: board.title,
                        message: board.contents,
                    });
                }
            });
        },
        dismissPopupToday: ({ commit }) => {
            commit("hidePopupAlert");
            commit("setPopupDismissedToday", true);
        },
    },
});
