'use client';
import { getMenuByMenuCode } from "@/apis/MenuAPI";
//useParams 썼기 때문에 use client 붙이기  13:19
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function MenuDetail () {
    //pathVariable 형태(예. /menu/2, /menu/12)로 이동하는
    // menu/{menuCode} url에 적용될 함수형 컴포넌트 MenuDetail
    // 동적 경로를 표시하는 것이기 때문에 폴더 이름을 []대괄호 안에 넣는다.
    // 예시 [menuCode] O    {menuCode} X
    //Use new hooks : useParams   13:19
    const {menuCode} = useParams();

    //console.log(menuCode);

    const [menu, setMenu] = useState();

    useEffect(() => {
        setMenu(getMenuByMenuCode(menuCode))
    }, [])

    return(
        //조건부 렌더링 처리 -> menu&& = menu가 있다면 ...실행
        menu && 
        <>
            <div>
                    <h1>{menu.menuName}</h1>
                    <h3>price: {menu.menuPrice}</h3>
                    <h3>Type of menu: {menu.categoryName}</h3>
                    <h3>Description  : {menu.detail.description}</h3>
                    <img src={menu.detail.image} wtyle={{maxWidth: 500}}/>
            </div>
        </>
    )
}