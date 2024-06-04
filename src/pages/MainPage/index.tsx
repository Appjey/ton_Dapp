import { FC, useState } from 'react';

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Stack, Typography } from '@mui/material';
import Steps from './Steps';

export const enum StepType {
	CONNECT = 'connect',
	WALLET_INFO = 'wallet-info',
}

const MainPage: FC = () => {
	const [step, setStep] = useState<StepType>(StepType.CONNECT);

	return (
		<Stack direction="column" justifyContent="center" height="100%" width={1280}>
			<Typography variant="h3" textAlign="center" mb={4}>
				designed by vacZWER
			</Typography>
			<Steps step={step} setStep={setStep} />
			<ToastContainer />
		</Stack>
	);
};

export default MainPage;
