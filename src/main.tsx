import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import {TonConnectUIProvider} from '@tonconnect/ui-react'

import './index.css'

import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

// this manifest is used temporarily for development purposes
const manifestUrl = 'https://app.ston.fi/tonconnect-manifest.json';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <TonConnectUIProvider manifestUrl={manifestUrl}>
        <App/>
    </TonConnectUIProvider>,
)
