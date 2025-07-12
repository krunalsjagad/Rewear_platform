document.addEventListener('DOMContentLoaded', () => {
  const itemsTab = document.getElementById('tab-items');
  const swapsTab = document.getElementById('tab-swaps');
  const itemsSection = document.getElementById('items-section');
  const swapsSection = document.getElementById('swaps-section');

  itemsTab.addEventListener('click', () => {
    itemsSection.style.display = 'block';
    swapsSection.style.display = 'none';
  });

  swapsTab.addEventListener('click', () => {
    swapsSection.style.display = 'block';
    itemsSection.style.display = 'none';
  });
});
