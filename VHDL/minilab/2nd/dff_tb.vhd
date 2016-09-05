entity dff_tb is
end entity;

architecture io of dff_tb is
  component dff is
    port( d,clk,rstb: in bit;
        q,not_q : out bit);
  end component;
  
  for all: dff use entity work.dff(dataflow);
  
  signal d,clk,rstb,q,q_not : bit;
  
  begin
    c1: dff port map (d,clk,rstb,q,q_not);
      
    d <= not d after 3ns;
    clk <= not clk after 5ns;
    rstb <= not rstb after 10ns;
    
end io;
    
    
      
