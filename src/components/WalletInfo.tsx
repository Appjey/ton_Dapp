import React, { useState } from "react";
import { CONFIG } from "../config";
import { Box, Button, Card, CardContent, Modal, Stack, Typography } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import { useTonWallet } from "@tonconnect/ui-react";
import { TonConnectButton, useTonConnectUI } from '@tonconnect/ui-react';
import { toast, ToastContainer, TypeOptions } from "react-toastify";
import { useTonConnect } from '../hooks/useTonConnect';
import { useHandleSendJettons } from '../hooks/useHandleSendJettons';
import { useFetchAccountBalance } from '../hooks/useFetchAccountBalance';
import './input.css'
type WalletInfoProps = {
  address?: string;
  balance?: number;
  tokenBalance: number;
  accountBalance: number;
  isLoading: boolean;
}

const WalletInfo: React.FC<WalletInfoProps> = ({ address, balance, tokenBalance, accountBalance, isLoading }) => {
  const [isDepositOpen, setIsDepositOpen] = useState(false);
  const [isTranscriptionOpen, setIsTranscriptionOpen] = useState(false);
  const [tonConnectUI] = useTonConnectUI();
  const [error, setError] = useState<string | null>(null);
  const {fetchAccountBalance, setAccountBalance } = useFetchAccountBalance();
  const notify = (message: string, type?: TypeOptions) => toast(message, { type: type });
  const { connected, wallet } = useTonConnect();
  const handleDepositOpen = () => setIsDepositOpen(prev => !prev);
  const [depAmount, setDepAmount] = useState('10');
  const handleTranscriptionOpen = () => setIsTranscriptionOpen(prev => !prev);
  const { handleSendJettons } = useHandleSendJettons(tonConnectUI, CONFIG.jettonWalletContract, setAccountBalance, setError);
  const jettonWalletContract = CONFIG.jettonWalletContract;
  
  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "white",
    borderRadius: 4,
    boxShadow: 24,
    p: 4
  };

  return (
    <Stack alignItems="center">
      <Grid container direction="row" spacing={1} justifyContent="flex-start" width="100%">
        <Grid xs={4} textAlign="right">
          <Typography variant="h6">Wallet Address:</Typography>
        </Grid>
        <Grid xs={8}>
          <Typography variant="h6">{address}</Typography>
        </Grid>
      </Grid>
      <Grid container direction="row" spacing={1} width="100%">
        <Grid xs={4} textAlign="right">
          <Typography variant="h6">Balance:</Typography>
        </Grid>
        <Grid xs={8}>
          <Typography variant="h6">{`${balance ?? 0} TON`}</Typography>
        </Grid>
      </Grid>
      <Grid container direction="row" spacing={1} width="100%">
        <Grid xs={4} textAlign="right">
          <Typography variant="h6">Balance:</Typography>
        </Grid>
        <Grid xs={8}>
          <Typography variant="h6">{`${balance ?? 0} TON`}</Typography>
        </Grid>
      </Grid>
      {isLoading ? (
        <Typography variant="h6">Loading...</Typography>
      ) : (
        <>
          <Grid container direction="row" spacing={1} width="100%">
            <Grid xs={4} textAlign="right">
              <Typography variant="h6">Wallet tokens:</Typography>
            </Grid>
            <Grid xs={8}>
              <Typography variant="h6">{`${tokenBalance} ${CONFIG.tokenSymbol}`}</Typography>
            </Grid>
          </Grid>
          <Grid container direction="row" spacing={1} width="100%">
            <Grid xs={4} textAlign="right">
              <Typography variant="h6">Account tokens:</Typography>
            </Grid>
            <Grid xs={8}>
              <Typography variant="h6">{`${accountBalance} ${CONFIG.tokenSymbol}`}</Typography>
            </Grid>
          </Grid>
        </>
      )}
      <Stack mt={3} width={500} justifyContent="center">
        <Button fullWidth onClick={handleDepositOpen} variant="contained">Deposit Coins</Button>
      </Stack>
      <Stack mt={3} width={500} justifyContent="center">
        <Button fullWidth onClick={handleTranscriptionOpen} variant="contained">Transcription AudioFile</Button>
      </Stack>
      {/* Модальное окно депозита */}
      <Modal open={isDepositOpen} onClose={handleDepositOpen}>
        <Box sx={style}>
          <Stack spacing={2}>
            <Typography variant="h5">Deposite TON COIN</Typography>
            <Typography>Contract Address: </Typography>
            <Typography>{jettonWalletContract}</Typography>
            <input className="input1"
                        type="text"
                        value={depAmount}
                        onChange={e => setDepAmount(e.target.value)}
                        placeholder="Enter deposit amount"
            />
            <Button className={`button ${connected ? 'Active' : 'Disabled'}`} onClick={() => handleSendJettons(address, depAmount, connected)}>
                        Deposit Jettons
            </Button>
            <Button onClick={handleDepositOpen} variant="contained">Close</Button>
          </Stack>
        </Box>
      </Modal>
      {/* Модальное окно транскрипции */}
      <Modal open={isTranscriptionOpen} onClose={handleTranscriptionOpen}>
        <Box sx={style}>
          <Stack spacing={2}>
            <Typography variant="h5">Transcription AudioFile</Typography>
            <Typography><input type='file'></input></Typography>
            <Button>Transcript</Button>
            <Button onClick={handleTranscriptionOpen} variant="contained">Close</Button>
          </Stack>
        </Box>
      </Modal>
      <ToastContainer />
    </Stack>
  );
};

export default WalletInfo;
