import menus from "../data/menu-detail.json"
// import json 파일 (파일 경로 이용)  250702 11:51
//menu data 전체 조회하는 함수 만든다 (not a component) 250702 11:50
export function getMenuList (){
    return menus;
}

//menu data specific look up을 위한 함수 정의 250702 13:26
export function getMenuByMenuCode(menuCode) {
    /**pathVariable menuCode를 전달받고 있다
     * 주의점 : URL 데이터는 문자열이다.
     */
    //전달받는 메뉴에 대한 타입 검사 13:26(2)
    console.log(typeof menuCode);  //menuCode = String 문자열  13:28
    return menus.filter(menu => menu.menuCode === parseInt(menuCode))[0];
    //내가 선택한 메뉴코드를 통한 상세조회가 필요한 거니까 .filter 사용  13:29
    /** filter :  배열에서 콜백함수가 true인 요소만 배열로 반환해준다.  13:31 */
}

//menuName을 전달받아 메뉴이름을 포함하고 있는 메뉴 목록을 반환하는
// searchMenu 함수 생성
export function searchMenu(searchMenuName) {
    //filter 사용. 
    //menus의 각 객체인 menu를 반복해서 돌면서
    //검색된 searchMenuName과 본래 menu의 menuName이 
    // .match 되는지 확인하고, match truthy한 값이 반환된다.  14:02
    return menus.filter(menu => menu.menuName.match(searchMenuName))
}