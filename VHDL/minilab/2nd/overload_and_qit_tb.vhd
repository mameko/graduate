use work.my_package.all;
entity overload_and_qit_tb is
end entity;

architecture io of overload_and_qit_tb is
  component overload_and_qit is
  end component;  
  
  signal a,b,f : qit;
  
  begin 
    a <= 'x', '0' after 40ns, '1' after 80ns, 'z' after 120ns;
    b <= 'x', '0' after 10ns, '1' after 20ns, 'z' after 30ns, 'x' after 40ns,'0' after 50ns,'1' after 60ns, 'z' after 70ns,'x' after 80ns,'0' after 90ns,'1' after 100ns, 'z' after 110ns,'x' after 120ns,'0' after 130ns,'1' after 140ns, 'z' after 150ns;
    f <= a and b;

end io;

  
