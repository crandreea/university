"use client"

import { useState} from "react"
import { useNavigate } from "react-router-dom"
import toast, {Toaster} from 'react-hot-toast'
import PlayerGames from "../components/table";
import "../styles/page.css"
import BoatPlacement from "../components/boatPlacement";

export default function Home() {
    const [activeTab, setActiveTab] = useState("list")

    const testPlayerId = 2;

    return (
        <main className="container">
            <card className="card">
                <tabs value={activeTab} onValueChange={setActiveTab} className="tabs-header">
                    <section>
                        <h2>Add New Boat (Test Placement)</h2>
                        <BoatPlacement/>
                    </section>
                    <section>
                        <h2>Player Games with Successful Shots (Player ID: {testPlayerId})</h2>
                        <PlayerGames playerId={testPlayerId}/>
                    </section>
                </tabs>
            </card>
            <Toaster/>
        </main>
    )
}
