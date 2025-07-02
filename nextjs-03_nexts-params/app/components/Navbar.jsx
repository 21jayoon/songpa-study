'use client';
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Navbar(){
    // .jsx파일로 만들면 jsx 사용에 더 적합한(?) 파일이 된다.
    // 그치만 .js 파일로 만들어도 괜찮다.  10:40

    //use pathname 사용 11:27
    const pathname = usePathname();  //현재 경로 가져오기

    //변수 하나 더 정의 11:28
    const isActive = (path) => pathname === path;

    //적용할 스타일 만들기  11:30
    const activeStyle = {
        backgroundColor : 'yellow',
        color : "red"
    }

    return(
        //get 방식이 작동하도록 /, /menu, /about 링크를 만든다. 10:42
        <>
            <div>
                <ul>
                    <li><Link href="/" style={isActive('/') ? activeStyle : undefined}>Main</Link></li>
                    <li><Link href="/menu" style={isActive('/menu') ? activeStyle : undefined}>Menu</Link></li>
                    <li><Link href="/about" style={isActive('/about') ? activeStyle : undefined}>Introduce</Link></li>
                </ul>
            </div>
        </>
    )
}