'use client';
import { useEffect, useState } from "react"
import { getMenuList } from "@/apis/MenuAPI";
import boxStyle from "./menu.module.css";
//import by boxStyle (이름은 그냥 마음대로 알아서 지정), menu.module.css  12:03
import MenuItem from "@/item/MenuItem";
import { useRouter } from "next/navigation";

export default function Menu(){
    //const {useState} = React;    html에서 쓰던 이 코드 필요 없음  
    // 바로 state 값 저장하면 import로 나옴 11:46
    const [menuList, setMenuList] = useState([]);

    //search 기능을 위해 state 값 하나 더 추가  13:42
    const [searchValue, setSearchValue] = useState([]);

    //router라는 상수 변수에 useRouter 소환  13:46(2)
    const router = useRouter();

    useEffect(() => {
        setMenuList(getMenuList())
    }, [])
    /* getMenuList()는 메뉴 데이터를 가져오는 함수입니다.
       처음 컴포넌트가 마운트될 때(setMenuList 호출 시점의 mount),
       비동기 데이터 로딩이기 때문에 메뉴 데이터가 준비되기 전까지 menuList는 빈 배열([])입니다.
       그래서 console.log(menuList)를 찍으면 처음엔 빈 배열이 출력됩니다.
       데이터가 로드되고 setMenuList가 실행되면 컴포넌트가 다시 렌더링되고, 
       이때 menuList에는 실제 메뉴 데이터가 들어갑니다.  11:58 */
   console.log(menuList); 
   /**console.log(menuList);에서 처음엔 빈 배열, 데이터 로드 후에야 메뉴 목록이 보임
 이는 React의 useEffect가 컴포넌트 마운트(즉, 화면에 처음 렌더링)될 때 한 번 실행되고,
  비동기 데이터가 준비되는 동안 menuList는 기본값(빈 배열)로 유지되기 때문. */

    //onChange에 넣어줄 onChangeHandler 만들기  250702 13:43
    const onChangeHandler = (e) =>{
        setSearchValue(e.target.value);
    }

    //button에 넣어줄 onClickHandler 만들기  13:44
    const onClickHandler =(e) => {
        //이번엔 검색 버튼 누를 시 url에 변화가 일어났음 좋겠다
        //-> 이때 사용할 수 있는 hook이 또 있음 :useRouter  13:46
        //router의 push라는 함수를 활용, 버튼에 이벤트를 준다. (url은 쿼리스트링 형태로 작성) 13:47
        router.push(`/menu/search/?menuName=${searchValue}`)
    }

    return(
        <>
            <h1>The menu page</h1>
            <div>
                <input 
                    type="search"
                    name="menuName"
                    value={searchValue}
                    onChange={onChangeHandler}
                />
                <button onClick={onClickHandler}>search</button>

            </div>
            <div className={boxStyle.MenuBox}>
                {/**여기서 쓰기 위해 src 하위에 item이란 폴더를 하나 생성,
                 * 하위에 MenuItem.jsx 파일을 만든다.  12:06
                 */}
                {menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
                {/** MenuItem 이름 그대로 위에서 import MenuItem 해준다. 12:11
                 * menu key 값 정하기 : menuCode로  
                 * 그리고 나머지를 menu라는 이름을 통해 value로 가져온다  12:09*/}
            </div>        
        </>
    )
}