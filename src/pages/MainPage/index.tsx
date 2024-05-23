import React, {FC, useState, useMemo} from 'react'

import ConnectStep from './Steps/ConnectStep';
import WalletInfoStep from './Steps/WalletInfo';

import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {Box, Container, Stack, Typography} from '@mui/material';
import Steps from "./Steps";

export const enum StepType {
    CONNECT = 'connect',
    WALLET_INFO = 'wallet-info'
}

type Props = {
    error: string | null;
}

const MainPage: FC<Props> = ({error}) => {
    const notify = () => toast(error, {type: 'error'})

    const [step, setStep] = useState<StepType>(StepType.CONNECT);

    return (
        <Stack direction='column' justifyContent='center' height='100%' width={1280}>
            <Typography variant='h3' textAlign='center' mb={4}>disigned by vacZWER</Typography>
            <Steps step={step} setStep={setStep}/>
            <ToastContainer/>
        </Stack>

    )
}

export default MainPage
