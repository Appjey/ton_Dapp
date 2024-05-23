import React, { FC, useCallback } from "react";
import { TonConnectButton, useTonWallet } from "@tonconnect/ui-react";
import { StepType } from "../..";

import { Stack, Typography, Button, Box } from "@mui/material";
import { toast, ToastContainer } from "react-toastify";

type Props = {
  setStep: (step: StepType) => void;
}

const ConnectStep: FC<Props> = ({ setStep }) => {
  const wallet = useTonWallet();

  const notify = () => toast("Error: Wallet doesn't connected", { type: "error" });

  const handleClickConnect = useCallback(() => {
    if (wallet) {
      setStep(StepType.WALLET_INFO);
    } else {
      notify();
    }
  }, [wallet, setStep]);

  return (
    <Box p={5} bgcolor="white" borderRadius={4}>
      <Stack spacing={4} alignItems="center">
        <Typography variant="h3">Scan QR-code and connect your TON-wallet</Typography>
        <TonConnectButton />
        <Box width={600}>
          <Button size="large" fullWidth color={wallet ? "success" : "error"} variant="contained"
                  onClick={handleClickConnect}>Next Step</Button>
        </Box>
        {/*<button className={`button ${connected ? 'Active' : 'Disabled'}`} onClick={handleClickConnect}>*/}
      </Stack>
      <ToastContainer position="bottom-center" />
    </Box>
  );
};

export default ConnectStep;
