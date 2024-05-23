import React, {useState } from 'react';

import MainPage from './pages/MainPage';

const App: React.FC = () => {
    const [error, setError] = useState<string | null>(null);

    return (
        <MainPage error={error} />
    );
};

export default App;
