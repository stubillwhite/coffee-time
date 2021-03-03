import React from 'react';
import Version from './components/Version';
import coffeetime from './apis/CoffeeTime';

class App extends React.Component {

  state = { serverVersion: null, clientVersion: null }

  readServerVersion = async (term) => {
    const response = await coffeetime.get('/version');

    const { version } = require('../package.json');

    this.setState({
      serverVersion: response.data,
      clientVersion: version
    });
  };

  componentDidMount() {
    this.readServerVersion();
  }

  render() {
    return (
      <div className="ui container">
        <div className="ui inverted vertical masthead center aligned segment">
          <div className="ui text container">
            <h1 className="ui inverted header">
              Coffee time!
          </h1>
            <div className="ui big image">
              <img src="logo.png" alt="TODO" />
            </div>
            <h2>Fancy a coffee with someone new?</h2>
          </div>
          <div>
            <div className="ui huge primary button">Let's go! <i className="right arrow icon"></i></div>
          </div>
        </div>
        <Version serverVersion={this.state.serverVersion} clientVersion={this.state.clientVersion} />
      </div>
    );
  }
}

export default App;
