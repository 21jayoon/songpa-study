import Image from "next/image";
import styles from "./page.module.css";
import Link from "next/link";

export default function Home() {
  return (
    <>
      <h1>The main page</h1>
      <nav>
        <Link href="/">HOME</Link><br/>
        <Link href="/about">Introducing</Link><br/>
        <Link href="/menu">MENU</Link><br/>
        <Link href="/dashboard">Dashboard</Link>
      </nav>
    </>
  )
}