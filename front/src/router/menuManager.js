import { computed } from "vue";
import store from "../stores/vuex.js";

// 메뉴 클릭 혹은 url로 접속 시 top, left 메뉴 css 적용
export function findTopMenuByPath(path) {
  const menusList = computed(() => store.state.menus);
  for (const topMenu of menusList.value) {
    if (topMenu.frontId === path) {
      setSelectedMenuByUrl(topMenu, topMenu.subMenuList[0]);
      return true;
    }

    const { subMenuList = [] } = topMenu;
    for (const subMenu of subMenuList) {
      if (subMenu.frontId === path) {
        setSelectedMenuByUrl(topMenu, subMenu);
        return true;
      }
    }
  }
  return false;
}

export function findTopMenuByRelatedMenuId(bundleId) {
  const frontRoutes = computed(() => store.state.frontRouters);
  const foundRoute = frontRoutes.value.find(route => route.frontId === bundleId);
  if (foundRoute) {
    if (foundRoute.relatedMenuId == null || foundRoute.relatedMenuId == "") {
      setSelectedMenuByUrl("", "");
    } else {
      findTopMenuByPath(foundRoute.relatedMenuId);
    }
  }
}

function setSelectedMenuByUrl(topMenu, subMenu) {
  store.commit("setSelectedTopMenu", topMenu);
  store.commit("setSelectedSubMenu", subMenu);
}
