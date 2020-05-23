import React from 'react';
import { render } from '@testing-library/react';
import App from './BookApp';

test('renders limit order book table name', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/Limit Order Book/);
  expect(linkElement).toBeInTheDocument();
});
