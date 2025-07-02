'use client'
import { useSearchParams } from "next/navigation"
import { useEffect, useState } from "react";
import MenuItem from "@/item/MenuItem";
import boxStyle from "../menu.module.css"
import { searchMenu } from "@/apis/MenuAPI";

export default function SearchMenu () {
    //또다른 hook useSearchParams 사용 13:53
    const searchParam = useSearchParams();

    //꺼내고 싶은 값의 key를 .get을 이용해 꺼내온다는 코드 작성 13:55
    const menuName = searchParam.get('menuName')
    // console.log(menuName);  '갈릭' 치면 console에 '갈릭'이라고 잘 뜸  13:58

    //검색해서 나올 메뉴리스트를 관리할 useState 값 하나 선언 13:59
    const [menuList, setMenuList] = useState([]);

    useEffect(() => {
        setMenuList(searchMenu(menuName));
    }, []);

    return(
        <>
            <h1>Search results</h1>
            {/**menu 폴더 하위 page.js에서 아래 코드 3줄 복사붙여넣기 14:13 */}
            <div className={boxStyle.MenuBox}>
            {menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
            </div>
        </>
    )
}