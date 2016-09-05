entity dff_struct_tb is
end entity;

architecture io of dff_struct_tb is
  component dff_struct is
    port( d,clk,rstb: in bit;
        q,not_q : out bit);
  end component;
  
  for all: dff_struct use entity work.dff_struct(struct);
  
  signal d,clk,rstb,q,q_not : bit;
  
  begin
    c1: dff_struct port map (d,clk,rstb,q,q_not);
      
    d <= not d after 40ns;
    clk <= not clk after 30ns;
    rstb <= not rstb after 20ns;
    
end io;
