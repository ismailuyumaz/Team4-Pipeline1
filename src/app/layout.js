import "./globals.css";
// import { Inter } from "next/font/google";
import { AuthContextProvider } from "./context/AuthContext";
import { SongContextProvider } from "./context/SongContext";
import { NextUIContextProvider } from "./context/NextUIContext";
// import Head from "next/head";

// const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Bussin' Bee",
  description: 'Generated by create next app',
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <link rel="manifest" href="/manifest.json" />
        <link rel="apple-touch-icon" href="/icon.png"></link>
        <title>Bussin' Bee</title>
        <meta name="description" content="Generated by create next app" />
      </head>
      <body className="bg-slate-300 flex flex-col">
        <AuthContextProvider>
          <SongContextProvider>
            <NextUIContextProvider>
              <div className="flex grow-0">
                <div className="grow">{children}</div>
              </div>
            </NextUIContextProvider>
          </SongContextProvider>
        </AuthContextProvider>
      </body>
    </html>
  );
}