import React, {FC, useMemo} from "react";
import ConnectStep from "./ConnectStep";
import WalletInfoStep from "./WalletInfo";
import {StepType} from "../index";

type Props = {
    step: StepType,
    setStep: (step: StepType) => void
}

const Steps: FC<Props> = ({step, setStep}) => {
    switch (step) {
        case StepType.CONNECT:
            return <ConnectStep setStep={setStep}/>
        case StepType.WALLET_INFO:
            return <WalletInfoStep setStep={setStep}/>
        default:
            return null;
    }
}

export default React.memo(Steps);

