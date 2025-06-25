"use client"

import { useState, useEffect } from "react"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"
import { Loader2 } from 'lucide-react'
import toast from 'react-hot-toast'
import "../styles/form.css"


const formSchema = z.object({
    tip: z.string().min(1, "Tipul este obligatoriu"),
    varstaId: z.string().min(1, "ID-ul categoriei de vârstă este obligatoriu"),
})

export function ProbaForm({ proba, onSubmitSuccess }) {
    const [isLoading, setIsLoading] = useState(false)


    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            tip: proba?.tip || "",
            varstaId: proba?.varsta?.id?.toString() || "",
        },
    })

    useEffect(() => {
        if (proba) {
            form.reset({
                tip: proba.tip || "",
                varstaId: proba.varsta?.id?.toString() || "",
            })
        } else {
            form.reset({
                tip: "",
                varstaId: "",
            })
        }
    }, [proba, form])

    async function onSubmit(values) {
        setIsLoading(true)
        try {
            const payload = {
                tip: values.tip,
                varsta: {
                    id: Number.parseInt(values.varstaId),
                },
            }

            const isUpdate = !!proba?.id;
            const url = isUpdate
                ? `http://localhost:8080/concurs/probe/${proba.id}`
                : "http://localhost:8080/concurs/probe";

            const method = proba?.id ? "PUT" : "POST"

            const response = await fetch(url, {
                method,
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            })

            if (!response.ok) {
                throw new Error(`Failed to ${proba?.id ? "update" : "create"} proba`)
            }

            toast( `Proba a fost ${proba?.id ? "modificată" : "adăugată"} cu succes`,)

            onSubmitSuccess()
        } catch (error) {
            toast(`Nu s-a putut ${proba?.id ? "modifica" : "adăuga"} proba. Încercați din nou.`,)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className="form-card">
            <div className="card-content">
                <form onSubmit={form.handleSubmit(onSubmit)} className="form">
                    <div className="form-item">
                        <label className="form-label">Tip</label>
                        <input className="form-input" placeholder="Introduceți tipul" {...form.register("tip")} />
                        {form.formState.errors.tip && <span className="form-message">{form.formState.errors.tip.message}</span>}
                    </div>

                    <div className="form-item">
                        <label className="form-label">ID Categorie Vârstă</label>
                        <input
                            className="form-input"
                            type="number"
                            placeholder="Introduceți ID-ul categoriei"
                            {...form.register("varstaId")}
                        />
                        {form.formState.errors.varstaId && (
                            <span className="form-message">{form.formState.errors.varstaId.message}</span>
                        )}
                    </div>

                    <div className="form-actions">
                        <button type="button" className="cancel-button" onClick={onSubmitSuccess}>
                            Anulare
                        </button>
                        <button type="submit" className="submit-button" disabled={isLoading}>
                            {isLoading && <Loader2 className="spinner" />}
                            {proba?.id ? "Modifică" : "Adaugă"} Proba
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}
