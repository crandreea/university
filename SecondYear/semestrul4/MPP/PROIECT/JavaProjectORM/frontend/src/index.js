import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Home from "./app/page";
import LoginForm from "./app/login-form";
import {BrowserRouter, Navigate, Routes, Route} from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/login" element={<LoginForm />} />
              <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
              <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
      </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
