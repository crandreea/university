"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import toast, {Toaster} from 'react-hot-toast'
import {ProbaSearch} from "../components/search";
import {ProbaTable} from "../components/table";
import {ProbaForm} from "../components/form";
import "../styles/page.css"

export default function Home() {
    const [probas, setProbas] = useState([])
    const [selectedProba, setSelectedProba] = useState(null)
    const [activeTab, setActiveTab] = useState("list")
    const navigate = useNavigate()

    const fetchProbas = async () => {
        try {
            const response = await fetch("http://localhost:8080/concurs/probe")
            if (!response.ok) {
                throw new Error("Failed to fetch data")
            }
            const data = await response.json()
            setProbas(data)
        } catch (error) {
            toast( "Nu s-au putut încărca datele. Încercați din nou.")
        }
    }

    useEffect(() => {
        const token = localStorage.getItem("token")
        if (!token) {
            window.location.href = "/login"
        } else {
            fetchProbas()
        }
    }, [])

    const handleEdit = (proba) => {
        setSelectedProba(proba)
        setActiveTab("form")
    }

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/concurs/probe/${id}`, {
                method: "DELETE",
            })

            if (!response.ok) {
                throw new Error("Failed to delete item")
            }

            toast("Proba a fost ștearsă cu succes")

            await fetchProbas()
        } catch (error) {
            toast("Nu s-a putut șterge proba. Încercați din nou.")
        }
    }

    const handleFormSubmit = () => {
        setSelectedProba(null)
        setActiveTab("list")
        fetchProbas()
    }


    const handleSearchResult = (proba) => {
        if (proba) {
            const updatedProbas = probas.map((p) => ({
                ...p,
                highlighted: p.id === proba.id,
            }))
            setProbas(updatedProbas)
        } else {
            toast( "Nu s-a găsit nicio probă cu acest ID")
        }
    }

    function handleAddNew() {
        setSelectedProba(null)
        setActiveTab("form")
    }

    function handleLogout() {
        localStorage.removeItem("token")
        navigate("/login")
    }

    return (
        <main className="container">
            <card className="card">
                <tabs value={activeTab} onValueChange={setActiveTab} className="tabs-header">
                    <div>Vizualizare, adăugare, modificare și ștergere probe</div>
                    <tabsContent value="form">
                        <ProbaForm proba={selectedProba} onSubmitSuccess={handleFormSubmit}/>
                    </tabsContent>
                    <div className="search-container">
                        <ProbaSearch onSearchResult={handleSearchResult}/>
                    </div>
                    <tabsContent value="list">
                        <ProbaTable probas={probas} onEdit={handleEdit} onDelete={handleDelete}/>
                    </tabsContent>
                    <div className="logout-container">
                        <button className="logout-button" onClick={handleLogout}>Logout</button>
                    </div>
                </tabs>
            </card>
            <Toaster/>
        </main>
    )
}
