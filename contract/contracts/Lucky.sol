
pragma solidity ^0.5.0;

contract lucky {

    address payable public owner;
    string  public standard    = 'Token 0.1';
    string  public name        = 'LuckyCoins';
    string  public symbol      = "LC";
    uint8   public decimals    = 0;
    uint256 public tokensPerOneEther = 100;




    mapping (address => uint256) private _balances;
    uint256 private _totalSupply;

    modifier onlyOwner {
        require(owner == msg.sender);
        _;
    }


    function totalSupply() public view returns (uint256) {
        return _totalSupply;
    }

    function balanceOf(address account) public view returns (uint256) {
        return _balances[account];
    }


    constructor() payable public {
        owner = msg.sender;
        _totalSupply = 100000;
        _balances[address(this)] = 100000;
    }


    event Transfer(address indexed from, address indexed to, uint256 value);


    function () payable external {
        require(_balances[address(this)] > 0);
        uint256 tokens = tokensPerOneEther * msg.value / 1000000000000000000;
        if (tokens > _balances[address(this)]) {
            tokens = _balances[address(this)];
            uint valueWei = tokens * 1000000000000000000 / tokensPerOneEther;
            msg.sender.transfer(msg.value - valueWei);
        }
        _balances[msg.sender] += tokens;
        _balances[address(this)] -= tokens;
        emit Transfer(address(this), msg.sender, 100);
    }


    function transfer(address _to, uint256 _value) public {
        require(_balances[msg.sender] >= _value);
        _balances[msg.sender] -= _value;
        _balances[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
    }


    function give(address _to, uint256 _value) onlyOwner  public  {
        require(_balances[address(this)] > 0);
        if (_value > _balances[address(this)]) {
            _value = _balances[address(this)];
        }
        _balances[address(this)] -= _value;
        _balances[_to] += _value;
        emit Transfer(address(this), _to, _value);
    }


    function take(address _from, uint256 _value) onlyOwner public  {
        require(_balances[_from] > 0);
        if (_value > _balances[_from]) {
            _value = _balances[_from];
        }
        _balances[_from] -= _value;
        _balances[address(this)] += _value;
        emit Transfer(_from, address(this), _value);
    }


    function withdraw(address payable _to) payable onlyOwner public  {
        _to.transfer(_balances[_to]*1000000000000000000/tokensPerOneEther);
        _balances[address(this)] +=_balances[_to];
        _balances[_to]=0;

    }

    function withdrawowner() payable onlyOwner public  {
        owner.transfer(address(this).balance);
    }



}

