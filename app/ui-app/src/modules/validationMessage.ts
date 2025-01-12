import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type ValidationMessageState = {
    [key: string]: {
        errors: string[];
    };
};

type SetMessagePayload = {
    group: string;
    errors: string[];
};

const initialState: ValidationMessageState = {};

const validationMessageSlice = createSlice({
    name: 'validationMessage',
    initialState,
    reducers: {
        setMessage: (state, action: PayloadAction<SetMessagePayload>) => {
            state[action.payload.group] = { errors: action.payload.errors };
        },
        removeMessage: (state, action: PayloadAction<string>) => {
            const keyToRemove = action.payload;
            Object.keys(state).forEach((key) => {
                if (key === keyToRemove) {
                    delete state[key];
                }
            });
        },
    },
});

export const { setMessage, removeMessage } = validationMessageSlice.actions;
export default validationMessageSlice.reducer;
