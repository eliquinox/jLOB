import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './BookApp';
import * as serviceWorker from './serviceWorker';
import { MuiThemeProvider } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import {theme} from "./theme";


ReactDOM.render(
  <React.StrictMode>
      <MuiThemeProvider theme={theme}>
        <CssBaseline>
            <App />
        </CssBaseline>
      </MuiThemeProvider>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
