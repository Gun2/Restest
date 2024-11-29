import React from 'react';
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox';
import {PaletteTree} from './palette';
import ValidationMessage from "../components/atoms/ValidationMessage";
import SchedulePage from "../components/pages/SchedulePage";
import AlertModal from "../components/organisms/AlertModal";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/ValidationMessage">
                <ValidationMessage/>
            </ComponentPreview>
            <ComponentPreview path="/SchedulePage">
                <SchedulePage/>
            </ComponentPreview>
            <ComponentPreview path="/Modal">
                <AlertModal/>
            </ComponentPreview>
        </Previews>
    );
};

export default ComponentPreviews;